/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.openai4j.chatcompletions;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.openai4j.Nullable;

/**
 * Represents a chat completion response returned by model, based on the provided input.
 */
@Value.Immutable(builder = false)
@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
@JsonDeserialize(as = ImmutableChatCompletionsResponse.class)
@Value.Enclosing
public interface ChatCompletionsResponse {

	/**
	 * A unique identifier for the chat completion.
	 */
	String id();

	/**
	 * A list of chat completion choices. Can be more than one if n is greater than 1.
	 */
	Choice[] choices();

	/**
	 * The Unix timestamp (in seconds) of when the chat completion was created.
	 */
	int created();

	/**
	 * The model used for the chat completion.
	 */
	String model();

	/**
	 * This fingerprint represents the backend configuration that the model runs with.
	 *
	 * Can be used in conjunction with the seed request parameter to understand when
	 * backend changes have been made that might impact determinism.
	 */
	@Nullable
	@JsonProperty("system_fingerprint")
	String systemFingerprint();

	/**
	 * The object type, which is always chat.completion.
	 */
	String object();

	/**
	 * Usage statistics for the completion request.
	 */
	Usage usage();

	@Value.Immutable(builder = false)
	@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
	@JsonDeserialize(as = ImmutableChatCompletionsResponse.Choice.class)
	interface Choice {
		/**
		 * The index of the choice in the list of choices.
		 */
		int index();

		/**
		 * The reason the model stopped generating tokens. This will be stop if the model
		 * hit a natural stop point or a provided stop sequence, length if the maximum
		 * number of tokens specified in the request was reached, content_filter if
		 * content was omitted due to a flag from our content filters, tool_calls if the
		 * model called a tool, or function_call (deprecated) if the model called a
		 * function.
		 */
		@JsonProperty("finish_reason")
		String finishReason();

		Message message();
	}

	@Value.Immutable(builder = false)
	@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
	@JsonDeserialize(as = ImmutableChatCompletionsResponse.Message.class)
	interface Message {
		/**
		 * The contents of the message.
		 */
		@Nullable
		String content();

		/**
		 * The tool calls generated by the model, such as function calls.
		 */
		@JsonProperty("tool_calls")
		List<ToolCall> toolCalls();

		/**
		 * The role of the author of this message.
		 */
		String role();
	}

	@Value.Immutable(builder = false)
	@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
	@JsonDeserialize(as = ImmutableChatCompletionsResponse.ToolCall.class)
	interface ToolCall {
		/**
		 * The ID of the tool call.
		 */
		String id();

		/**
		 * The type of the tool. Currently, only function is supported.
		 */
		String type();

		Function function();
	}

	@Value.Immutable(builder = false)
	@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
	@JsonDeserialize(as = ImmutableChatCompletionsResponse.Function.class)
	interface Function {
		/**
		 * The name of the function to call.
		 */
		String name();

		/**
		 * The arguments to call the function with, as generated by the model in JSON
		 * format. Note that the model does not always generate valid JSON, and may
		 * hallucinate parameters not defined by your function schema. Validate the
		 * arguments in your code before calling your function.
		 */
		String arguments();
	}

	@Value.Immutable(builder = false)
	@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
	@JsonDeserialize(as = ImmutableChatCompletionsResponse.Usage.class)
	interface Usage {
		/**
		 * Number of tokens in the generated completion.
		 */
		@JsonProperty("completion_tokens")
		int completionTokens();

		/**
		 * Number of tokens in the prompt.
		 */
		@JsonProperty("prompt_tokens")
		int promptTokens();

		/**
		 * Total number of tokens used in the request (prompt + completion).
		 */
		@JsonProperty("total_tokens")
		int totalTokens();
	}
}
