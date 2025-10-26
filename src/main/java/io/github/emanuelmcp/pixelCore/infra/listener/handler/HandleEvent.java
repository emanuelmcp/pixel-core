package io.github.emanuelmcp.pixelCore.infra.listener.handler;

public interface HandleEvent <T> {
    void handle(T event);
}
