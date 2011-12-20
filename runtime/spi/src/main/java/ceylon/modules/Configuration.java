/*
 * Copyright 2011 Red Hat inc. and third party contributors as noted 
 * by the author tags.
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
package ceylon.modules;

import ceylon.modules.spi.Argument;
import ceylon.modules.spi.ArgumentType;
import ceylon.modules.spi.Constants;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Stephane Epardaud
 */
public class Configuration {

    /**
     * Ceylon module to run
     */
    public String module;

    /**
     * Ceylon program arguments
     */
    public String[] arguments;

    /**
     * List of module repositories
     */
    public List<String> repositories = new LinkedList<String>();

    /**
     * Name of class or toplevel method to run
     */
    public String run;

    /**
     * Source path
     */
    public String src;

    // Non-Ceylon
    public String executable;
    public boolean cacheContent;
    /**
     * names of implementation classes by interface name
     */
    public Map<String, String> impl = new HashMap<String, String>();

    /**
     * Sets an argument and checks its required number of parameters
     *
     * @param name   argument name without the prefix -/+
     * @param type   type of option
     * @param values list of arguments
     * @param i      index of the argument name
     * @return the index of the last argument required eaten by this argument
     */
    public int setArgument(String name, ArgumentType type, String[] values, int i) {
        Argument argument = Argument.forArgumentName(name, type);
        if (argument == null)
            throw new CeylonRuntimeException("Unknown argument: " + name);

        if (i + argument.getRequiredValues() >= values.length)
            throw new CeylonRuntimeException("Missing argument value: " + name);

        int arg = i + 1;
        switch (argument) {
            case EXECUTABLE:
                executable = values[arg];
                break;
            case CACHE_CONTENT:
                cacheContent = true;
                break;
            case IMPLEMENTATION:
                impl.put(values[arg], values[arg + 1]);
                break;
            case SOURCE:
                src = values[arg];
                break;
            case RUN:
                run = values[arg];
                break;
            case REPOSITORY:
                repositories.add(values[arg]);
                break;
            case HELP:
                printUsage();
                break;
            case VERSION:
                printVersion();
                break;
        }

        return i + argument.getRequiredValues();
    }

    private void printVersion() {
        System.out.println("Version: ceylon " + Constants.CEYLON_VERSION);
        System.exit(0);
    }

    public void printUsage() {
        System.err.print("Usage: ceylon [options...] moduleName/version [args...]\n"
                + "\n"
                + " -run qualified-name: Name of a class or toplevel method to run\n"
                + "                      (default: use the module descriptor)\n"
                + " -rep path:           Module repository path (can be specified more than once)\n"
                + "                      (default: ...)\n"
                + " -src path:           Source path (default: source)\n"
                + " -help:               Prints help usage\n"
                + " -version:            Prints version number\n"
                + " moduleName/version:  Module name and version to run (required)\n"
        );
        System.exit(1);
    }

    public void check() {
        if (executable == null
                || executable.isEmpty()) {
            System.err.println("Missing +executable parameter\n");
            printUsage();
        }
        if (module == null
                || module.isEmpty()) {
            System.err.println("Missing module name\n");
            printUsage();
        }
    }
}
