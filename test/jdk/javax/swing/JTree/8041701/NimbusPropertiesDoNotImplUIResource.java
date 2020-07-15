/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.UIResource;

/**
 * @test
 * @bug 8041701
 * @summary  Nimbus JTree renderer properties persist across L&F changes
 * @key headful
 * @run main NimbusPropertiesDoNotImplUIResource
 */

public class NimbusPropertiesDoNotImplUIResource {
    private static final String[] defPropertyKeys = new String[] {
            "Tree.leafIcon", "Tree.closedIcon",
            "Tree.openIcon", "Tree.selectionForeground",
            "Tree.textForeground", "Tree.selectionBackground",
            "Tree.textBackground", "Tree.selectionBorderColor"};

    public static void main(String[] args) throws Exception {
        UIManager.LookAndFeelInfo[] installedLookAndFeels;
        installedLookAndFeels = UIManager.getInstalledLookAndFeels();

        for (UIManager.LookAndFeelInfo LF : installedLookAndFeels) {
            try {
                UIManager.setLookAndFeel(LF.getClassName());
                for (String propertyKey : defPropertyKeys) {
                    verifyProperty(propertyKey);
                }
            } catch(UnsupportedLookAndFeelException e) {
                System.out.println("Note: LookAndFeel " + LF.getClassName()
                        + " is not supported on this configuration");
            }
        }

    }

    private static void verifyProperty(String propertyKey) {
        Object property = UIManager.get(propertyKey);
        if (property == null) {
            return;
        }
        if (!(property instanceof UIResource)) {
            throw new RuntimeException("Property '"+ propertyKey
                    +"' is instance of '"+property.getClass()
                    +"' instead of UIResource.");
        }
    }
}
