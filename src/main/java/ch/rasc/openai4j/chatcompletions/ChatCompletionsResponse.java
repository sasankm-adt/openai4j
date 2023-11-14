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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.common.FunctionArguments;

/**
 * Represents a chat completion response returned by model, based on the provided input.
 */
public record ChatCompletionsResponse(String id, List<Choice> choices, int created,
		String model, @JsonProperty("system_fingerprint") String systemFingerprint,
		String object, Usage usage) {

	/**
	 * A unique identifier for the chat completion.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * A list of chat completion choices. Can be more than one if n is greater than 1.
	 */
	@Override
	public List<Choice> choices() {
		return this.choices;
	}

	/**
	 * The Unix timestamp (in seconds) of when the chat completion was created.
	 */
	@Override
	public int created() {
		return this.created;
	}

	/**
	 * The model used for the chat completion.
	 */
	@Override
	public String model() {
		return this.model;
	}

	/**
	 * This fingerprint represents the backend configuration that the model runs with.
	 * 
	 * Can be used in conjunction with the seed request parameter to understand when
	 * backend changes have been made that might impact determinism.
	 */
	@Override
	public String systemFingerprint() {
		return this.systemFingerprint;
	}

	/**
	 * The object type, which is always chat.completion.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * Usage statistics for the completion request.
	 */
	@Override
	public Usage usage() {
		return this.usage;
	}

	public record Choice(int index,
			@JsonProperty("finish_reason") FinishReason finishReason, Message message) {

		/**
		 * The index of the choice in the list of choices.
		 */
		@Override
		public int index() {
			return this.index;
		}

		/**
		 * The reason the model stopped generating tokens. This will be stop if the model
		 * hit a natural stop point or a provided stop sequence, length if the maximum
		 * number of tokens specified in the request was reached, content_filter if
		 * content was omitted due to a flag from our content filters, tool_calls if the
		 * model called a tool, or function_call (deprecated) if the model called a
		 * function.
		 */
		@Override
		public FinishReason finishReason() {
			return this.finishReason;
		}

		/**
		 * A chat completion message generated by the model.
		 */
		@Override
		public Message message() {
			return this.message;
		}

		public enum FinishReason {
			STOP("stop"), LENGTH("length"), CONTENT_FILTER("content_filter"),
			TOOL_CALLS("tool_calls"), FUNCTION_CALL("function_call");

			private final String value;

			FinishReason(String value) {
				this.value = value;
			}

			@JsonValue
			public String value() {
				return this.value;
			}
		}
	}

	public record Message(String content,
			@JsonProperty("tool_calls") List<ToolCall> toolCalls, String role) {
		/**
		 * The contents of the message.
		 */
		@Override
		public String content() {
			return this.content;
		}

		/**
		 * The tool calls generated by the model, such as function calls.
		 */
		@Override
		public List<ToolCall> toolCalls() {
			return this.toolCalls;
		}

		/**
		 * The role of the author of this message.
		 */
		@Override
		public String role() {
			return this.role;
		}
	}

	public record ToolCall(String id, String type, FunctionArguments function) {
		/**
		 * The ID of the tool call.
		 */
		@Override
		public String id() {
			return this.id;
		}

		/**
		 *
		 * The type of the tool. Currently, only function is supported.
		 */
		@Override
		public String type() {
			return this.type;
		}

		/**
		 * The function that the model called.
		 */
		@Override
		public FunctionArguments function() {
			return this.function;
		}
	}

	public record Usage(@JsonProperty("completion_tokens") int completionTokens,
			@JsonProperty("prompt_tokens") int promptTokens,
			@JsonProperty("total_tokens") int totalTokens) {
		/**
		 * Number of tokens in the generated completion.
		 */
		@Override
		public int completionTokens() {
			return this.completionTokens;
		}

		/**
		 * Number of tokens in the prompt.
		 */
		@Override
		public int promptTokens() {
			return this.promptTokens;
		}

		/**
		 * Total number of tokens used in the request (prompt + completion).
		 */
		@Override
		public int totalTokens() {
			return this.totalTokens;
		}

	}
}
