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
package ch.rasc.openai4j.vectorstores;

import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.common.ListRequest;
import ch.rasc.openai4j.common.ListResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Beta
public interface VectorStoresClient {

	/**
	 * Returns a list of vector stores.
	 *
	 * @return A list of vector store objects.
	 */
	@RequestLine("GET /vector_stores")
	ListResponse<VectorStore> list();

	/**
	 * Returns a list of vector stores.
	 *
	 * @return A list of vector store objects.
	 */
	@RequestLine("GET /vector_stores")
	ListResponse<VectorStore> list(@QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of vector stores.
	 *
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of vector store objects.
	 */
	default ListResponse<VectorStore> list(ListRequest request) {
		return this.list(request.toMap());
	}

	/**
	 * Returns a list of vector stores.
	 *
	 * @param fn A list request object with configuration for paging and ordering
	 * @return A list of vector store objects.
	 */
	default ListResponse<VectorStore> list(
			Function<ListRequest.Builder, ListRequest.Builder> fn) {
		return this.list(fn.apply(ListRequest.builder()).build());
	}

	/**
	 * Create a vector store.
	 *
	 * @return A vector store object.
	 */
	@RequestLine("POST /vector_stores")
	@Headers("Content-Type: application/json")
	VectorStore create(VectorStoreCreateRequest request);

	/**
	 * Create a vector store.
	 *
	 * @return A vector store object.
	 */
	default VectorStore create(
			Function<VectorStoreCreateRequest.Builder, VectorStoreCreateRequest.Builder> fn) {
		return this.create(fn.apply(VectorStoreCreateRequest.builder()).build());
	}

	/**
	 * Retrieves a vector store.
	 *
	 * @return The vector store object matching the specified ID.
	 */
	@RequestLine("GET /vector_stores/{vector_store_id}")
	VectorStore retrieve(@Param("vector_store_id") String vectorStoreId);

	/**
	 * Modifies a message.
	 *
	 * @return The modified message object.
	 */
	@RequestLine("POST /vector_stores/{vector_store_id}")
	@Headers("Content-Type: application/json")
	VectorStore modify(@Param("vector_store_id") String vectorStoreId,
			VectorStoreModifyRequest request);

	/**
	 * Modifies a vector store.
	 * 
	 * @return The modified vector store object.
	 */
	default VectorStore modify(String vectorStoreId,
			Function<VectorStoreModifyRequest.Builder, VectorStoreModifyRequest.Builder> fn) {
		return this.modify(vectorStoreId,
				fn.apply(VectorStoreModifyRequest.builder()).build());
	}

	/**
	 * Delete a vector store.
	 * 
	 * @return Deletion status
	 */
	@RequestLine("DELETE /vector_stores/{vector_store_id}")
	@Headers("Content-Type: application/json")
	DeletionStatus delete(@Param("vector_store_id") String vectorStoreId);

}
