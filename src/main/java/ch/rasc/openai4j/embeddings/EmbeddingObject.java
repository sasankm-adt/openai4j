package ch.rasc.openai4j.embeddings;

import java.util.Arrays;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents an embedding vector returned by embedding endpoint.
 */
@Value.Immutable(builder = false)
@Value.Style(visibility = ImplementationVisibility.PACKAGE, allParameters = true)
@JsonDeserialize(as = ImmutableEmbeddingObject.class)
public interface EmbeddingObject {

	/**
	 * The index of the embedding in the list of embeddings.
	 */
	int index();

	/**
	 * The embedding vector, which is a list of floats. The length of vector depends on
	 * the model as listed in the embedding guide.
	 */
	Embedding embedding();

	class Embedding {
		private final String base64;
		private final double[] doubleArray;

		@JsonCreator
		Embedding(double[] doubleArray) {
			this.doubleArray = doubleArray;
			this.base64 = null;
		}

		@JsonCreator
		Embedding(String base64) {
			this.doubleArray = null;
			this.base64 = base64;
		}

		public double[] doubleArray() {
			return this.doubleArray;
		}

		public String base64() {
			return this.base64;
		}

		@Override
		public String toString() {
			return "Embedding [embeddingBase64=" + this.base64 + ", embedding="
					+ Arrays.toString(this.doubleArray) + "]";
		}

	}

	/**
	 * The object type, which is always "embedding".
	 */
	String object();
}
