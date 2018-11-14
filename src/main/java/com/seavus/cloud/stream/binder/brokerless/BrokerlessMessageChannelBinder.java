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

package com.seavus.cloud.stream.binder.brokerless;

import java.util.List;
import java.util.concurrent.Executor;
import org.springframework.cloud.stream.binder.AbstractBinder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.DefaultBinding;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.Assert;

public class BrokerlessMessageChannelBinder
        extends AbstractBinder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private MessageChannelResolver messageChannelResolver;
    private Executor executor;

    public BrokerlessMessageChannelBinder(MessageChannelResolver messageChannelResolver, Executor executor) {
        this.messageChannelResolver = messageChannelResolver;
        this.executor = executor;
    }

    @Override
    protected Binding<MessageChannel> doBindConsumer(String name, String group, MessageChannel inputTarget,
            ConsumerProperties properties) {
        return new DefaultBinding<>(name, group, inputTarget, null);
    }

    @Override
    protected Binding<MessageChannel> doBindProducer(String name, MessageChannel outputChannel,
            ProducerProperties properties) {
        Assert.isInstanceOf(SubscribableChannel.class,
                outputChannel,
                "Binding is supported only for SubscribableChannel instances");
        List<MessageChannel> messageChannels = messageChannelResolver.resolveByDestination(name);
        ((SubscribableChannel) outputChannel).subscribe(new BrokerlessMessageHandler(messageChannels, executor));

        return new DefaultBinding<>(name, null, outputChannel, null);
    }

}

