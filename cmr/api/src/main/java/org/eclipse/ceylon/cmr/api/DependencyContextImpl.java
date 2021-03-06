/*
 * Copyright 2014 Red Hat inc. and third party contributors as noted
 * by the author tags.
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

package org.eclipse.ceylon.cmr.api;

import org.eclipse.ceylon.model.cmr.ArtifactResult;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class DependencyContextImpl implements DependencyContext {
    private final ArtifactResult result;
    private boolean ignoreInner;
    private boolean ignoreExternal;

    DependencyContextImpl(ArtifactResult result, boolean ignoreInner, boolean ignoreExternal) {
        this.result = result;
        this.ignoreInner = ignoreInner;
        this.ignoreExternal = ignoreExternal;
    }

    @Override
    public ArtifactResult result() {
        return result;
    }

    public boolean ignoreInner() {
        return ignoreInner;
    }

    public boolean ignoreExternal() {
        return ignoreExternal;
    }
}
