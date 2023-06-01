// -*- tab-width:8; indent-tabs-mode:nil; c-basic-offset:4; -*-
// vim: set sw=4 ts=8 et:
/*
 * The MIT License (MIT)
 *
 * Copyright (c)
 *   2014 Martin Lukasiewycz <lukasiewycz@gmail.com>
 *   2018 Felix Reimann <felix@fex-it.de>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.sf.opendse.tutorial;

import net.sf.opendse.io.SpecificationWriter;
import net.sf.opendse.model.Application;
import net.sf.opendse.model.Architecture;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.visualization.SpecificationViewer;

public class Part1 {

    public static void main(String[] args) {

        /*
         * The application is defined by data-dependent tasks. In general, two
         * tasks have to be implemented either on the same or adjacent
         * resources.
         */
        Application<Task, Dependency> application = new Application<Task, Dependency>();
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        application.addVertex(t1);
        application.addVertex(t2);
        application.addEdge(new Dependency("d1"), t1, t2);

        /*
         * The architecture is defined by resources that can be linked (linked
         * resources are considered to have a way to communicate). Note that it
         * is possible to set attributes to each resources like the costs inthis
         * case. Attributes might be integers, doubles, or strings. It is also
         * possible to set attributes of tasks, mappings, etc.
         */
        Architecture<Resource, Link> architecture = new Architecture<Resource, Link>();
        Resource r1 = new Resource("r1");
        r1.setAttribute("costs", 100);
        Resource r2 = new Resource("r2");
        r2.setAttribute("costs", 50);
        Link l1 = new Link("l1");
        architecture.addVertex(r1);
        architecture.addVertex(r2);
        architecture.addEdge(l1, r1, r2);

        /*
         * The mappings define how tasks are mapped to resources. For a
         * specification is it possible to define more than one possible mapping
         * for a task such that the optimization selects the optimal mapping.
         */
        Mappings<Task, Resource> mappings = new Mappings<Task, Resource>();
        Mapping<Task, Resource> m1 = new Mapping<Task, Resource>("m1", t1, r1);
        Mapping<Task, Resource> m2 = new Mapping<Task, Resource>("m2", t2, r2);
        mappings.add(m1);
        mappings.add(m2);

        /*
         * The specification consists of the application, architecture, and
         * mappings. Additionally it is possible to specify routings for
         * communication tasks.
         */
        Specification specification = new Specification(application, architecture, mappings);

        /*
         * It is possible to write the specification to a file. Correspondingly,
         * the class SpecificationReader can read classes.
         */
        SpecificationWriter writer = new SpecificationWriter();
        writer.write(specification, "specs/Specification1.xml");

        /*
         * It is also possible to view the specification in a GUI.
         */
        SpecificationViewer.view(specification);

    }
}
