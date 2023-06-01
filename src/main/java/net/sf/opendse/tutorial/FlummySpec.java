// -*- tab-width:8; indent-tabs-mode:nil; c-basic-offset:4; -*-
// vim: set sw=4 ts=8 et:
/*
 * Copyright (c)
 *   2021 FAU -- Benjamin Hackenberg <benjamin.hackenberg@fau.de>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package net.sf.opendse.tutorial;

import net.sf.opendse.model.Application;
import net.sf.opendse.model.Architecture;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.optimization.SpecificationWrapper;

public class FlummySpec implements SpecificationWrapper {

    @Override
    public Specification getSpecification() {
        /*
         * An application might be extended with communication tasks.
         * Communication tasks enable the communication over multiple resources.
         */
        Application<Task, Dependency> application = new Application<Task, Dependency>();
        Task t1 = new Task("t1");
        Communication c1 = new Communication("c1");
        Task t2 = new Task("t2");
        application.addVertex(t1);
        application.addVertex(t2);
        application.addVertex(c1);
        application.addEdge(new Dependency("d1"), t1, c1);
        application.addEdge(new Dependency("d2"), c1, t2);
        /*
         */
        Architecture<Resource, Link> architecture = new Architecture<Resource, Link>();
        Resource r1 = new Resource("r1");
        r1.setAttribute("costs", 100);
        Resource r2 = new Resource("r2");
        r2.setAttribute("costs", 50);
        Resource bus = new Resource("bus");
        bus.setAttribute("costs", 20);
        Link l1 = new Link("l1");
        Link l2 = new Link("l2");
        architecture.addVertex(r1);
        architecture.addVertex(bus);
        architecture.addVertex(r2);
        architecture.addEdge(l1, r1, bus);
        architecture.addEdge(l2, bus, r2);
        /*
         */
        Mappings<Task, Resource> mappings = new Mappings<Task, Resource>();
        Mapping<Task, Resource> m1 = new Mapping<Task, Resource>("m1", t1, r1);
        Mapping<Task, Resource> m2 = new Mapping<Task, Resource>("m2", t2, r2);
        mappings.add(m1);
        mappings.add(m2);
        /*
         * The additional parameter Routings for a specification might be used
         * to restrict where communication tasks can be routed. If we do not
         * pass the Routings object, all communication tasks can be routed over
         * any resource.
         */
        return new Specification(application, architecture, mappings);
    }

}
