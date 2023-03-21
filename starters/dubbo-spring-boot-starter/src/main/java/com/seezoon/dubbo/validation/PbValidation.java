package com.seezoon.dubbo.validation;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

/**
 * validation extension
 * <p/>
 * <b>yaml usage</b>
 * <pre>
 *  validation: pbValidation
 * </pre>
 *
 * @author dfenghuang
 * @date 2023/3/20 22:03
 * @see Validator
 */
public class PbValidation implements Validation {

    private static final Validator validator = new PbValidator();

    @Override
    public Validator getValidator(URL url) {
        return validator;
    }
}
