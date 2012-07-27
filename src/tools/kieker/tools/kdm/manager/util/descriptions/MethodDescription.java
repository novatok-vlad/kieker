/***************************************************************************
 * Copyright 2012 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.tools.kdm.manager.util.descriptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.eclipse.gmt.modisco.omg.kdm.code.AbstractCodeElement;
import org.eclipse.gmt.modisco.omg.kdm.code.ArrayType;
import org.eclipse.gmt.modisco.omg.kdm.code.Datatype;
import org.eclipse.gmt.modisco.omg.kdm.code.ExportKind;
import org.eclipse.gmt.modisco.omg.kdm.code.MethodKind;
import org.eclipse.gmt.modisco.omg.kdm.code.MethodUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.ParameterKind;
import org.eclipse.gmt.modisco.omg.kdm.code.ParameterUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.PrimitiveType;
import org.eclipse.gmt.modisco.omg.kdm.code.Signature;

import kieker.tools.kdm.manager.KDMModelManager;

/**
 * This class provides a description of a {@link MethodUnit}. It also contains the unique method qualifier necessary to use other iterators form the
 * KDMModelManager.
 * 
 * @author Benjamin Harms
 * 
 */
public class MethodDescription {
	/**
	 * This is the logger which can be used to log messages. The logged messages will be written into a suitable log file.
	 */
	private static final Logger LOG = Logger.getLogger(MethodDescription.class.getName());
	/**
	 * The original method unit from kdm model.
	 */
	private final MethodUnit methodUnit;
	/**
	 * The special qualifier used within the KDMModelManager.
	 */
	private final String methodQualifier;
	/**
	 * If true, the return type is a primitive like int, boolean or char. Otherwise it is something like class or interface type.
	 */
	private boolean arrayReturnType;
	/**
	 * The full name of the return type.
	 */
	private String fullReturnTypeName;

	static {
		// Initialize the logger by using a file-handler to store the log-messages.
		try {
			MethodDescription.LOG.addHandler(new FileHandler(MethodDescription.class.getSimpleName() + ".log"));
		} catch (final SecurityException ex) {
			ex.printStackTrace();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Creates a new instance of this class from the given {@link MethodUnit} and the method qualifier used within the KDMModelManager.
	 * 
	 * @param methodUnit
	 *            The method unit form the kdm model.
	 * @param methodQualifier
	 *            The method qualifier used within the KDMModelManager.
	 */
	public MethodDescription(final MethodUnit methodUnit, final String methodQualifier) {
		this.methodUnit = methodUnit;
		this.methodQualifier = methodQualifier;

		if (!MethodKind.CONSTRUCTOR.equals(methodUnit.getKind())) {
			// Find the signature
			final Iterator<AbstractCodeElement> elementIterator = methodUnit.getCodeElement().iterator();
			while (elementIterator.hasNext()) {
				final AbstractCodeElement codeElement = elementIterator.next();
				if (codeElement instanceof Signature) {
					final Signature signature = (Signature) codeElement;
					// Try to extract the type
					if (this.extraktReturnType(signature)) {
						break;
					}
				}
			}

			if ("".equals(this.fullReturnTypeName)) {
				MethodDescription.LOG.severe("The return type of the method couldn't be found.");
			}
		} else {
			this.arrayReturnType = false;
			this.fullReturnTypeName = "";
		}
	}

	/**
	 * This method tries to extract the full name of the return type of a signature. It returns true if it was successful, otherwise false.
	 * 
	 * @param signature
	 *            The signature to extract the return type.
	 * @return
	 *         True if it was successful, otherwise false.
	 */
	private boolean extraktReturnType(final Signature signature) {
		// Find the return type
		final Iterator<ParameterUnit> parameterIterator = signature.getParameterUnit().iterator();
		while (parameterIterator.hasNext()) {
			final ParameterUnit parameterUnit = parameterIterator.next();
			if (ParameterKind.RETURN.equals(parameterUnit.getKind())) {
				final Datatype returnType = parameterUnit.getType();
				String name = returnType.getName();
				final StringBuilder fullName = new StringBuilder();
				// Check primitive type
				if (returnType instanceof PrimitiveType) {
					this.arrayReturnType = false;
				} else if (returnType instanceof ArrayType) { // Check array type
					this.arrayReturnType = true;
					final ArrayType arrayType = (ArrayType) returnType;
					final Datatype itemType = arrayType.getItemUnit().getType();
					// Use the item type name
					name = itemType.getName();
					if (!(itemType instanceof PrimitiveType)) { // No primitive type
						fullName.append(KDMModelManager.reassembleFullParentName(itemType));
					}
				} else { // anything else
					fullName.append(KDMModelManager.reassembleFullParentName(returnType));
				}
				// Assemble full name
				if ((fullName.length() > 0) && !fullName.toString().endsWith(".")) {
					fullName.append('.');
				}
				fullName.append(name);
				this.fullReturnTypeName = fullName.toString();
				// The type could be extracted
				return true;
			}
		}

		// The type could not be extracted
		return false;
	}

	/**
	 * This method returns the visibility modifier of the class.
	 * 
	 * @return
	 *         The visibility modifier.
	 */
	public ExportKind getVisibilityModifier() {
		return this.methodUnit.getExport();
	}

	/**
	 * This method returns the kind of the method.
	 * 
	 * @return
	 *         The kind of the method.
	 */
	public MethodKind getMethodKind() {
		return this.methodUnit.getKind();
	}

	/**
	 * This method returns the name of the method.
	 * 
	 * @return
	 *         The name of the method.
	 */
	public String getName() {
		return this.methodUnit.getName();
	}

	/**
	 * This method returns the method qualifier used within the KDMModelManager.
	 * 
	 * @return
	 *         The method qualifier used within the KDMModelManager.
	 */
	public String getMethodQualifier() {
		return this.methodQualifier;
	}

	/**
	 * This method returns the return type of the method.
	 * 
	 * @return
	 *         The return type of the method.
	 */
	public String getReturnType() {
		return this.fullReturnTypeName;
	}

	/**
	 * This method returns true if the return type of the method is an array type, otherwise false.
	 * 
	 * @return
	 *         True if the return type is an array type, otherwise false.
	 */
	public boolean isArrayReturnType() {
		return this.arrayReturnType;
	}

	/**
	 * This method returns true if the method is a constructor, otherwise false.
	 * 
	 * @return
	 *         True if the method is a constructor, otherwise false.
	 */
	public boolean isConstructor() {
		return MethodKind.CONSTRUCTOR.equals(this.getMethodKind());
	}
}