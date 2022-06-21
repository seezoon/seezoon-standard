package com.seezoon.maven.generator.io;

import java.io.IOException;

import com.seezoon.maven.generator.plan.TablePlan;

/**
 * 代码生成
 *
 * @author hdf
 */
public interface CodeGenerator {

    void generate(TablePlan... tablePlans) throws IOException;
}
