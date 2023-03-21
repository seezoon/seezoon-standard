package com.seezoon.dubbo.validation;

import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.ValidatorIndex;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.validation.Validator;

/**
 * validator for protobuf
 *
 * @author dfenghuang
 * @date 2023/3/20 22:14
 * @see <a href="https://github.com/bufbuild/protoc-gen-validate">...</a>
 */
public class PbValidator implements Validator {

    private static final ValidatorIndex validatorIndex = new ReflectiveValidatorIndex();
    private static final Map<Class<?>, io.envoyproxy.pgv.Validator<Object>> validators = new ConcurrentHashMap<>();

    @Override
    public void validate(String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Exception {
        if (parameterTypes.length == 0) {
            return;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            Object argument = arguments[i];
            if (null == argument) {
                continue;
            }
            Class<?> parameterType = parameterTypes[i];
            io.envoyproxy.pgv.Validator<Object> validator = validators.computeIfAbsent(parameterType,
                    key -> validatorIndex.validatorFor(key));
            if (null == validator) {
                throw new RpcException(RpcException.VALIDATION_EXCEPTION,
                        "no validator found for " + parameterType.getName());
            }
            try {
                validator.assertValid(argument);
            } catch (ValidationException e) {
                throw new RpcException(RpcException.VALIDATION_EXCEPTION,
                        "rpc method " + methodName + " validate failed due to "
                                + e.getField() + " " + e.getReason(), e);
            }
        }
    }
}
