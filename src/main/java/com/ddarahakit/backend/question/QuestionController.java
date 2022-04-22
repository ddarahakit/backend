package com.ddarahakit.backend.question;


import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.course.model.GetCourseWithImageRes;
import com.ddarahakit.backend.question.model.GetQuestionRes;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.user.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_INVALID_IMAGE;
import static com.ddarahakit.backend.config.BaseResponseStatus.RESPONSE_NULL_ERROR;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostQuestionRes> createQuestion(@AuthenticationPrincipal LoginUser loginUser, @RequestBody PostQuestionReq postQuestionReq) {
        try {
            PostQuestionRes postQuestionRes = questionService.createQuestion(loginUser.getEmail(), postQuestionReq);
            return new BaseResponse<>(postQuestionRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/delete/{questionIdx}")
    public BaseResponse<PostQuestionRes> deleteQuestion(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Integer questionIdx) {
        try {
            PostQuestionRes postQuestionRes = questionService.deleteQuestion(loginUser.getEmail(), questionIdx);
            if (postQuestionRes.getStatus() == 1) {
                return new BaseResponse<>(postQuestionRes);
            } else {
                return new BaseResponse<>(RESPONSE_NULL_ERROR);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetQuestionRes>> getQuestionList() {
        try {

            List<GetQuestionRes> getQuestionResList = questionService.getQuestionList();
            return new BaseResponse<>(getQuestionResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{chapterIdx}")
    public BaseResponse<List<GetQuestionRes>> getQuestionListByUserEmail(@PathVariable Integer chapterIdx) {
        try {

            List<GetQuestionRes> getQuestionResList = questionService.getQuestionListByChapter(chapterIdx);
            return new BaseResponse<>(getQuestionResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/user")
    public BaseResponse<List<GetQuestionRes>> getQuestionListByUserEmail(@AuthenticationPrincipal LoginUser loginUser) {
        try {

            List<GetQuestionRes> getQuestionResList = questionService.getQuestionListByUserEmail(loginUser.getEmail());
            return new BaseResponse<>(getQuestionResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}


