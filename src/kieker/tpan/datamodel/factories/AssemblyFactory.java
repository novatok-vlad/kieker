package kieker.tpan.datamodel.factories;

import java.util.Collection;
import java.util.Hashtable;
import kieker.tpan.datamodel.AssemblyComponent;
import kieker.tpan.datamodel.ComponentType;

/*
 * ==================LICENCE=========================
 * Copyright 2006-2010 Kieker Project
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
 * ==================================================
 */

/**
 *
 * @author Andre van Hoorn
 */
public class AssemblyFactory extends AbstractSystemSubFactory{
    private final Hashtable<String, AssemblyComponent>
            assemblyComponentInstancesByName =
            new Hashtable<String, AssemblyComponent>();
    private final Hashtable<Integer, AssemblyComponent>
            assemblyComponentInstancesById = new Hashtable<Integer, AssemblyComponent>();

    public final AssemblyComponent rootAssemblyComponent;

    public AssemblyFactory(final SystemEntityFactory systemFactory,
            final AssemblyComponent rootAssemblyComponent){
        super(systemFactory);
        this.rootAssemblyComponent = rootAssemblyComponent;
    }

    /** Returns the instance for the passed factoryIdentifier; null if no instance
     *  with this factoryIdentifier.
     */
    public final AssemblyComponent getAssemblyComponentInstanceByFactoryIdentifier(final String factoryIdentifier){
        return this.assemblyComponentInstancesByName.get(factoryIdentifier);
    }

    public final AssemblyComponent createAndRegisterAssemblyComponentInstance(
            final String factoryIdentifier,
            final ComponentType componentType){
            AssemblyComponent newInst;
            if (this.assemblyComponentInstancesByName.containsKey(factoryIdentifier)){
                throw new IllegalArgumentException("Element with name " + factoryIdentifier + "exists already");
            }
            int id = this.getAndIncrementNextId();
            newInst = new AssemblyComponent(id, "@"+id, componentType);
            this.assemblyComponentInstancesById.put(id, newInst);
            this.assemblyComponentInstancesByName.put(factoryIdentifier, newInst);
            return newInst;
    }

    public final Collection<AssemblyComponent> getAssemblyComponentInstances(){
        return this.assemblyComponentInstancesById.values();
    }
}
