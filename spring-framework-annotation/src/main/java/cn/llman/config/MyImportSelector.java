package cn.llman.config;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义逻辑 返回需要导入的组件
 *
 * @author
 * @date 2018/12/19
 */
public class MyImportSelector implements ImportSelector {

    /**
     * 返回值就是要导入到容器中的组件的全类名
     * AnnotationMetadata
     *
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"cn.llman.bean.Cat", "cn.llman.bean.Dog"};
    }
}
