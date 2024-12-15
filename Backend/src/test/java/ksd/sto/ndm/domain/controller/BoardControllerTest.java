package ksd.sto.ndm.domain.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ksd.sto.ndm.domain.dto.BoardDTO;
import ksd.sto.ndm.domain.service.BoardService;

@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BoardController boardController;
    
    @Mock
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        //MockMvc는 @AutoConfigureMockMvc에 의해 자동으로 구성되므로 별도 설정 불필요
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

//    @Test
    void test() throws Exception {
        mockMvc
            .perform(get("/api/"))
            .andExpect(status().isOk())
//            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0]").value("data1"));
    }

    @Test
    void test_fileAll() throws Exception {
        // Given
//        List<BoardDTO> mockBoardList = Arrays.asList(new BoardDTO(1L, "Title", "Content","writer",0,new Date(),new Date()));
//        when(boardService.getAllBoardList()).thenReturn(mockBoardList);        
        mockMvc
            .perform(get("/api/list").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FOT05ZTU9VUyJdLCJzdWIiOiJhbm9ueW1vdXNVc2VyIiwiaWF0IjoxNzMzNDgzOTQ0LCJleHAiOjE3MzM1NzAzNDR9.rt3xsQZWMI6FQ7raH5WG2o9CGrWrAw36g7onjK1L3z4"))
            .andDo(print())
            .andExpect(jsonPath("$[0].boardId", is(1)));
    }
}
