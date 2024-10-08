/*
 * JOPA Examples
 * Copyright (C) 2024 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package cz.cvut.kbss.jopa.example01;

public class Example {

    private static final String MANUAL = "manual";
    private static final String GENERATED = "generated";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Missing example type argument.");
            printUsageInfo();
            System.exit(1);
        }
        final String command = args[0];
        Runner runner = null;
        switch (command) {
            case MANUAL:
                runner = new ManualRunner();
                break;
            case GENERATED:
                runner = new GeneratedRunner();
                break;
            default:
                printUsageInfo();
                break;
        }
        if (runner != null) {
            runner.run();
        }
    }

    private static void printUsageInfo() {
        System.out.println("Possible arguments: ");
        System.out.println("   '" + MANUAL + "'     - Executes the demo with manually written data model.");
        System.out.println("   '" + GENERATED + "'  - Executes the demo with data model generated by OWL2Java.");
    }
}
