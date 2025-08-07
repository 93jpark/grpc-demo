package com.example.userclient.service;

import com.example.grpc.GetStreamMessage;
import com.example.grpc.StreamMessage;
import com.example.grpc.UserGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SseService {

    @GrpcClient("user")
    private UserGrpc.UserStub stub; // 반드시 UserStub(비동기)로 해야 스트리밍됨!

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        String subscriberId = UUID.randomUUID().toString();

        // 스트림 구독 시작 (구독 단위로 별도 요청)
        stub.streamUserMessages(
                GetStreamMessage.newBuilder()
                        .setSubscriber(subscriberId)
                        .build(),
                new StreamObserver<StreamMessage>() {
                    @Override
                    public void onNext(StreamMessage value) {
                        try {
                            emitter.send(SseEmitter.event().data(value.getMessage()));
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        emitter.completeWithError(t);
                    }

                    @Override
                    public void onCompleted() {
                        emitter.complete();
                    }
                }
        );

        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(emitter::complete);

        return emitter;
    }


}
