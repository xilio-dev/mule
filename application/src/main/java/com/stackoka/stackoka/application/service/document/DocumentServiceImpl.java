package com.stackoka.stackoka.application.service.document;


import com.stackoka.stackoka.common.data.document.Document;
import com.stackoka.stackoka.repository.document.DocumentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

}
