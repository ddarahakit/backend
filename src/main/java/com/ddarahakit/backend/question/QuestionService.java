package com.ddarahakit.backend.question;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.model.GetCourseWithImageRes;
import com.ddarahakit.backend.question.model.GetQuestionRes;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import com.ddarahakit.backend.question.model.PutQuestionReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.ddarahakit.backend.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public PostQuestionRes createQuestion(String userEmail, PostQuestionReq postQuestionReq) throws BaseException {
        try {
            return questionDao.createQuestion(userEmail, postQuestionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostQuestionRes updateQuestion(String userEmail, Integer questionIdx, PutQuestionReq putQuestionReq) throws BaseException {
        try {
            return questionDao.updateQuestion(userEmail, questionIdx, putQuestionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostQuestionRes deleteQuestion(String userEmail, Integer questionIdx) throws BaseException {
        try {
            return questionDao.deleteQuestion(userEmail, questionIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetQuestionRes getQuestion(Integer questionIdx) throws BaseException {
        try {
            GetQuestionRes getQuestionRes = questionDao.getQuestion(questionIdx);

            return getQuestionRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetQuestionRes> getQuestionList() throws BaseException {
        try {
            List<GetQuestionRes> getQuestionResList = questionDao.getQuestionList();

            return getQuestionResList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetQuestionRes> getQuestionListByChapter(Integer chapterIdx) throws BaseException {
        try {
            List<GetQuestionRes> getQuestionResList = questionDao.getQuestionListByChapter(chapterIdx);

            return getQuestionResList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetQuestionRes> getQuestionListByUserEmail(String userEmail) throws BaseException {
        try {
            List<GetQuestionRes> getQuestionResList = questionDao.getQuestionListByUserEmail(userEmail);

            return getQuestionResList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
