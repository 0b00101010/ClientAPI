/*
 * Copyright 2017 ZeroMemes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zero.client.api.command;

import me.zero.client.api.command.exception.CommandInitException;

/**
 * @author Brady
 * @since 5/31/2017 8:55 AM
 */
public abstract class Command implements ICommand {

    private final String[] headers;
    private final String description;
    private final String[] syntax;

    public Command() {
        if (!this.getClass().isAnnotationPresent(Cmd.class))
            throw new RuntimeException(new CommandInitException(this, "@Cmd annotation not found!"));

        Cmd cmd = this.getClass().getAnnotation(Cmd.class);
        this.headers = cmd.headers();
        this.description = cmd.description();
        this.syntax = cmd.syntax();
    }

    @Override
    public String[] headers() {
        return this.headers;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public String[] syntax() {
        return this.syntax;
    }
}
