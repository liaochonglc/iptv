package com.ido.iptv;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.TypeUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan(basePackages = "com.ido.iptv.dao")
public class IptvApplication extends WebMvcConfigurationSupport {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

	public static void main(String[] args) {
		SpringApplication.run(IptvApplication.class, args);
	}

	/**
	 * use fastjson to parse json mime
	 *
	 * @param converters
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		//字符串、字节数组解析器
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setWriteAcceptCharset(false);  // see SPR-7316

		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(stringHttpMessageConverter);

		//json格式解析器
		// 1.需要先定义一个convert 转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastJson的配置信息,比如，是否需要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");


		// 空值特别处理
		// WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
		// WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
		// WriteNullNumberAsZero 将数值类型字段的空值输出为0
		// WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
		// SerializerFeature.PrettyFormat 格式化
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteMapNullValue
		);

		TypeUtils.compatibleWithJavaBean = true;

		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);

		// 3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);

		// 4.将convert添加到converters当中
		converters.add(fastConverter);
	}

    /**
     * 静态资源拦截策略
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/tempFile/");

        if (swaggerEnable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
