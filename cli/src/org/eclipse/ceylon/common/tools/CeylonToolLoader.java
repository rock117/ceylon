/*
 * Copyright Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the authors tag. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License version 2.
 * 
 * This particular file is subject to the "Classpath" exception as provided in the 
 * LICENSE file that accompanied this code.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License,
 * along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.eclipse.ceylon.common.tools;

import org.eclipse.ceylon.common.tool.ServiceToolLoader;
import org.eclipse.ceylon.common.tool.Tool;

public class CeylonToolLoader extends ServiceToolLoader {

    public CeylonToolLoader() {
        super(Tool.class);
    }

    public CeylonToolLoader(ClassLoader loader) {
        super(loader, Tool.class);
    }

    @Override
    protected String getToolClassName(String toolName) {
        if (toolName == null || toolName.isEmpty()) {
            return CeylonTool.class.getName();
        } else {
            return super.getToolClassName(toolName);
        }
    }

    @Override
    public String getToolName(String className) {
        return classNameToToolName(className);
    }
}