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
import java.util.Map;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.openai4j.Nullable;

@Value.Immutable
@Value.Style(visibility = ImplementationVisibility.PACKAGE, depluralize = true)
@JsonSerialize(as = ImmutableChatCompletionsCreateRequest.class)
@JsonInclude(Include.NON_EMPTY)
public interface ChatCompletionsCreateRequest {

	enum ResponseFormat {
		TEXT(Map.of("type", "text")), JSON_OBJECT(Map.of("type", "json_object"));

		private final Map<String, String> value;

		ResponseFormat(Map<String, String> value) {
			this.value = value;
		}

		@JsonValue
		public Map<String, String> toValue() {
			return this.value;
		}
	}

	/**
	 * A list of messages comprising the conversation so far.
	 */
	List<ChatCompletionMessage> messages();

	/**
	 * ID of the model to use. See the model endpoint compatibility table for details on
	 * which models work with the Chat API.
	 */
	String model();

	/**
	 * Number between -2.0 and 2.0. Positive values penalize new tokens based on their
	 * existing frequency in the text so far, decreasing the model's likelihood to repeat
	 * the same line verbatim. Defaults to 0
	 */
	@JsonProperty("frequency_penalty")
	@Nullable
	Double frequencyPenalty();

	/**
	 * Modify the likelihood of specified tokens appearing in the completion.
	 *
	 * Accepts a JSON object that maps tokens (specified by their token ID in the
	 * tokenizer) to an associated bias value from -100 to 100. Mathematically, the bias
	 * is added to the logits generated by the model prior to sampling. The exact effect
	 * will vary per model, but values between -1 and 1 should decrease or increase
	 * likelihood of selection; values like -100 or 100 should result in a ban or
	 * exclusive selection of the relevant token.
	 */
	@JsonProperty("logit_bias")
	@Nullable
	Map<String, Double> logitBias();

	/**
	 * The maximum number of tokens to generate in the chat completion. The total length
	 * of input tokens and generated tokens is limited by the model's context length.
	 * Example Python code for counting tokens. Defaults to inf
	 */
	@JsonProperty("max_tokens")
	@Nullable
	Integer maxTokens();

	/**
	 * How many chat completion choices to generate for each input message. Defaults to 1
	 */
	@Nullable
	Integer n();

	/**
	 * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether
	 * they appear in the text so far, increasing the model's likelihood to talk about new
	 * topics. Defaults to 0
	 */
	@JsonProperty("presence_penalty")
	@Nullable
	Double presencePenalty();

	/**
	 * An object specifying the format that the model must output. Setting to { "type":
	 * "json_object" } enables JSON mode, which guarantees the message the model generates
	 * is valid JSON.
	 *
	 * Important: when using JSON mode, you must also instruct the model to produce JSON
	 * yourself via a system or user message. Without this, the model may generate an
	 * unending stream of whitespace until the generation reaches the token limit,
	 * resulting in increased latency and appearance of a "stuck" request. Also note that
	 * the message content may be partially cut off if finish_reason="length", which
	 * indicates the generation exceeded max_tokens or the conversation exceeded the max
	 * context length.
	 *
	 */
	@Nullable
	@JsonProperty("response_format")
	ResponseFormat responseFormat();

	/**
	 * This feature is in Beta. If specified, our system will make a best effort to sample
	 * deterministically, such that repeated requests with the same seed and parameters
	 * should return the same result. Determinism is not guaranteed, and you should refer
	 * to the system_fingerprint response parameter to monitor changes in the backend.
	 */
	@Nullable
	Integer seed();

	/**
	 * Up to 4 sequences where the API will stop generating further tokens.
	 */
	@Nullable
	List<String> stop();

	/**
	 * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent
	 * as data-only server-sent events as they become available, with the stream
	 * terminated by a data: [DONE] message. Example Python code.
	 */
	@Nullable
	Boolean stream();

	/**
	 * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make
	 * the output more random, while lower values like 0.2 will make it more focused and
	 * deterministic. We generally recommend altering this or top_p but not both. Defaults
	 * to 1
	 */
	@Nullable
	Double temperature();

	/**
	 * An alternative to sampling with temperature, called nucleus sampling, where the
	 * model considers the results of the tokens with top_p probability mass. So 0.1 means
	 * only the tokens comprising the top 10% probability mass are considered. We
	 * generally recommend altering this or temperature but not both. Defaults to 1
	 */
	@Nullable
	@JsonProperty("top_p")
	Double topP();

	/**
	 * A list of tools the model may call. Currently, only functions are supported as a
	 * tool. Use this to provide a list of functions the model may generate JSON inputs
	 * for.
	 */
	@Nullable
	List<ChatCompletionTool> tools();

	/**
	 * Controls which (if any) function is called by the model. none means the model will
	 * not call a function and instead generates a message. auto means the model can pick
	 * between generating a message or calling a function. Specifying a particular
	 * function via {"type: "function", "function": {"name": "my_function"}} forces the
	 * model to call that function.
	 *
	 * none is the default when no functions are present. auto is the default if functions
	 * are present.
	 */
	@Nullable
	@JsonProperty("tool_choice")
	ToolChoice toolChoice();

	class ToolChoice {
		private final Object value;

		ToolChoice(Object value) {
			this.value = value;
		}

		/**
		 * none means the model will not call a function and instead generates a message
		 */
		public static ToolChoice none() {
			return new ToolChoice("none");
		}

		/**
		 * auto means the model can pick between generating a message or calling a
		 * function.
		 */
		public static ToolChoice auto() {
			return new ToolChoice("auto");
		}

		/**
		 * Specifying a particular function via {"type: "function", "function": {"name":
		 * "my_function"}} forces the model to call that function.
		 */
		public static ToolChoice function(String functionName) {
			return new ToolChoice(
					Map.of("type", "function", "function", Map.of("name", functionName)));
		}

		@JsonValue
		public Object value() {
			return this.value;
		}
	}

	/**
	 * A unique identifier representing your end-user, which can help OpenAI to monitor
	 * and detect abuse.
	 */
	@Nullable
	String user();

	static Builder builder() {
		return new Builder();
	}

	final class Builder extends ImmutableChatCompletionsCreateRequest.Builder {
	}

}
