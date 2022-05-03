package com.ddarahakit.backend.question;


import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.question.model.GetQuestionRes;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.question.model.PutQuestionReq;
import com.ddarahakit.backend.user.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ddarahakit.backend.config.BaseResponseStatus.RESPONSE_NULL_ERROR;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;


    // 질문 생성
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

    // 질문 조회
    @ResponseBody
    @GetMapping("/{questionIdx}")
    public BaseResponse<GetQuestionRes> getQuestion(@PathVariable Integer questionIdx) {
        try {
            System.out.println("==================================="+questionIdx);
            GetQuestionRes getQuestionRes = questionService.getQuestion(questionIdx);
            return new BaseResponse<>(getQuestionRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 질문 수정 PUT
    @ResponseBody
    @PutMapping("/update/{questionIdx}")
    public BaseResponse<PostQuestionRes> updateQuestion(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Integer questionIdx, @RequestBody PutQuestionReq putQuestionReq) {
        try {
            PostQuestionRes postQuestionRes = questionService.updateQuestion(loginUser.getEmail(), questionIdx, putQuestionReq);
            return new BaseResponse<>(postQuestionRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 질문 삭제
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

    // 질문 목록
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

    // 질문 목록(챕터별)
    @ResponseBody
    @GetMapping("/chapter/{chapterIdx}")
    public BaseResponse<List<GetQuestionRes>> getQuestionListByChapter(@PathVariable Integer chapterIdx) {
        try {

            List<GetQuestionRes> getQuestionResList = questionService.getQuestionListByChapter(chapterIdx);
            return new BaseResponse<>(getQuestionResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 질문 목록(사용자별)
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


