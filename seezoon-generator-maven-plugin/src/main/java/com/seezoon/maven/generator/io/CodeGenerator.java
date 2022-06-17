package com.seezoon.maven.generator.io;

import com.seezoon.maven.generator.plan.TablePlan;
import java.io.IOException;

/**
 * 代码生成
 *
 * @author hdf
 */
public interface CodeGenerator {

    void generate(TablePlan... tablePlans) throws IOException;
}
