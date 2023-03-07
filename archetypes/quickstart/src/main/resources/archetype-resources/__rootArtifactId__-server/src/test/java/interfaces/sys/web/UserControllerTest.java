#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.interfaces.sys.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seezoon.ddd.dto.Response;
import ${package}.BaseSpringApplicationTest;
import ${package}.application.sys.UserApplicationService;
import ${package}.application.sys.dto.AddUserCmd;

@Disabled
class UserControllerTest extends BaseSpringApplicationTest {

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser() throws Exception {
        when(userApplicationService.addUser(any(AddUserCmd.class))).thenReturn(Response.success());

        mockMvc
            .perform(post("/sys/user/add_user").content(objectMapper.writeValueAsString(new AddUserCmd()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("${symbol_dollar}.code").value(0));

        verify(userApplicationService, atLeastOnce()).addUser(any(AddUserCmd.class));
    }

    @Test
    void testAddUser() {}

    @Test
    void modifyUserMobile() {}

    @Test
    void qryUserPage() {}
}