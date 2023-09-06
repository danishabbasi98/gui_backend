package com.vodacom.er.gui.service;

import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EsFunctionsService {
    @Autowired
    private EsFunctionsRepository esFunctionsRepository;

    Date currentDate = new Date();

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Functions saveFunction(Functions functions) {
        logger.info("EsFunctionsService : saveFunction() Method Called :  ");
        Functions _functions = new Functions();
        _functions.setLink(functions.getLink());
        _functions.setName(functions.getName());
        _functions.setDescription(functions.getDescription());
        _functions.setModified_date(currentDate);
        _functions.setModified_by("admin");
        _functions.setActive_yn("");
        logger.info("EsFunctionsService : saveFunction() Method Called : Successfully ");
        return esFunctionsRepository.save(_functions);
    }

    public List<Functions> allFunctions() {
        logger.info("EsFunctionsService : allFunctions() Method Called : Successfully ");
        return esFunctionsRepository.findAll();
    }

    public Optional<Functions> getFunctionById(Integer id) {
        logger.info("EsFunctionsService : getFunctionById() Method Called : Successfully ");
        return esFunctionsRepository.findById(id);
    }
    public Optional<Functions> getActualFunctionById(Integer id) {
        logger.info("EsFunctionsService : getActualFunctionById() Method Called : Successfully ");
        return esFunctionsRepository.findById(id);
    }


    public Optional<Functions> updateEsFunction(Functions _functions) {
        logger.info("EsFunctionsService : updateEsFunction() Method Called :  ");
        Optional<Functions> stu = esFunctionsRepository.findById(_functions.getSeq());
        Functions s = stu.get();
        s.setSeq(_functions.getSeq());
        s.setDescription(_functions.getDescription());
        s.setLink(_functions.getLink());
        s.setName(_functions.getName());
        s.setActive_yn(_functions.getActive_yn());
        s.setModified_date(_functions.getModified_date());
        s.setModified_by(_functions.getModified_by());
        esFunctionsRepository.save(s);
        logger.info("EsFunctionsService : updateEsFunction() Method Called : Successfully ");
        return esFunctionsRepository.findById(_functions.getSeq());
    }

    public void  deleteEsFunctionById(Integer id) {
        logger.info("EsFunctionsService : deleteEsFunctionById() Method Called : Successfully ");
        esFunctionsRepository.deleteById(id);
    }

    public Optional<Functions> deleteReturnEsFunctionById(Integer id) {
        logger.info("EsFunctionsService : deleteReturnEsFunctionById() Method Called :  ");
        Optional<Functions> es_functions = esFunctionsRepository.findById(id);
        if(!es_functions.isPresent()){
            logger.info("EsFunctionsService : deleteReturnEsFunctionById() Method Called : Invalid Id ");
            return null;
        }
        logger.info("EsFunctionsService : deleteReturnEsFunctionById() Method Called : Successfully ");
        esFunctionsRepository.deleteById(id);
        return es_functions;
    }

}
