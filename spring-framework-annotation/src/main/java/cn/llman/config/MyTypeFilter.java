package cn.llman.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

/**
 * @author
 * @date 2018/12/19
 */
public class MyTypeFilter implements TypeFilter {

    /**
     * @param metadataReader        读取当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取其他类的信息的工厂
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类资源的信息(类路径等信息)
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("CLASSNAME INNER: ---> " + className);
        if (className.contains("BookDao")) {
            return false;
        }

        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        annotationTypes.forEach(annotationType -> System.out.println("    ANNOTATIONTYPE INNER: ---> " + annotationType));
        return true;
    }
}
