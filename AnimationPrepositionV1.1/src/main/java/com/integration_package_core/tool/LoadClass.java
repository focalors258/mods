package com.integration_package_core.tool;

import org.checkerframework.checker.units.qual.C;

public class LoadClass {





        /**
         * 检查指定类是否存在（即联动模组是否加载）
         */
        public static Class<?> on(String className) {
            try {
                // 尝试加载类，若不存在会抛出ClassNotFoundException
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }
