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

import net.sf.opendse.io.SpecificationReader;
import net.sf.opendse.model.Specification;
import net.sf.opendse.realtime.et.PriorityScheduler;
import net.sf.opendse.realtime.et.qcqp.MyEncoder.OptimizationObjective;
import net.sf.opendse.visualization.SpecificationViewer;

public class Part7 {

    public static void main(String[] args) throws Exception {
        /*
         * The implementation contains information about the execution time of
         * tasks (e) and their period (h). The periods of a function have to be
         * all the same. Additionally, tasks have deadlines (deadlines).
         * Finally, based on the type of scheduler (FIXEDPRIORITY_NONPREEMPTIVE
         * or FIXEDPRIORITY_PREEMPTIVE) on the resources, the scheduling is
         * performed. Note that the deadline has to be less or equal to the
         * period of a task.
         */
        SpecificationReader reader = new SpecificationReader();
        Specification implementation = reader.read("specs/Implementation7.xml");

        /*
         * The scheduling determines priorities for all tasks. For messages,
         * multiple priorities are assigned per resource it passes.
         */
        PriorityScheduler scheduler = new PriorityScheduler(implementation);
        scheduler.solve(OptimizationObjective.DELAY);

        SpecificationViewer.view(implementation);
    }

}
