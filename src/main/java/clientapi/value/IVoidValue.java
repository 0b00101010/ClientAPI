/*
 * Copyright 2018 ImpactDevelopment
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
 */

package clientapi.value;

/**
 * @author Brady
 * @since 9/27/2018
 */
public interface IVoidValue extends IValue<Void> {

    @Override
    default Void getValue() {
        return null;
    }

    @Override
    default void setValue(Void value) {
        throw new UnsupportedOperationException("Attempted to set the value of an IVoidValue");
    }
}
