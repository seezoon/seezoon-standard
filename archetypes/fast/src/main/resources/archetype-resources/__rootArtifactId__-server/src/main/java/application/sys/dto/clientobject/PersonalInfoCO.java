#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.dto.clientobject;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 * 
 * @author dfenghuang
 * @date 2022/11/24 23:22
 */
@Getter
@Setter
public class PersonalInfoCO {
    private UserCO info;
    private List<String> roles = Collections.emptyList();
    private List<String> permissions = Collections.emptyList();
}
