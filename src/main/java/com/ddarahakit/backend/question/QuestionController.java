package com.ddarahakit.backend.question;


import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostQuestionRes> createQuestion(@RequestBody PostQuestionReq postQuestionReq) {
        try {
            PostQuestionRes postQuestionRes = questionService.createQuestion(postQuestionReq);
            return new BaseResponse<>(postQuestionRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}


