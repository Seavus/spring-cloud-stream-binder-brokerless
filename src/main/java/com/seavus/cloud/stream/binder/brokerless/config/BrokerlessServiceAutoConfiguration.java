/*
 * Copyright (c) 2018 Seavus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.seavus.cloud.stream.binder.brokerless.config;

import com.seavus.cloud.stream.binder.brokerless.BrokerlessMessageChannelBinder;
import com.seavus.cloud.stream.binder.brokerless.MessageChannelResolver;
import java.util.concurrent.Executors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnMissingBean(Binder.class)
@Import({PropertyPlaceholderAutoConfiguration.class})
public class BrokerlessServiceAutoConfiguration {

    @Bean
    public MessageChannelResolver messageSubscriberRegistry(BindingServiceProperties bindingServiceProperties,
            ApplicationContext applicationContext) {
        return new MessageChannelResolver(bindingServiceProperties, applicationContext);
    }

    @Bean
    public BrokerlessMessageChannelBinder brokerlessMessageChannelBinder(MessageChannelResolver messageChannelResolver) {
        BrokerlessMessageChannelBinder messageChannelBinder = new BrokerlessMessageChannelBinder(messageChannelResolver,
                Executors.newCachedThreadPool());
        return messageChannelBinder;
    }
}
