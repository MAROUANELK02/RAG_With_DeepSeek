<h1>Building a RAG Chatbot with Spring Boot, Langchain4j, and Ollama</h1>
<p>This project demonstrates the creation of a Retrieval-Augmented Generation (RAG) chatbot using Spring Boot, Langchain4j, and Ollama. The chatbot provides context-aware responses by leveraging large language models (LLMs) and retrieval mechanisms.</p>

<h2>Overview</h2>
<p>Retrieval-Augmented Generation (RAG) combines the strengths of information retrieval and natural language generation. This project integrates:</p>
<ul>
    <li><strong>Spring Boot</strong>: A Java-based framework for building web applications.</li>
    <li><strong>Langchain4j</strong>: A Java library for integrating LLMs into applications.</li>
    <li><strong>Ollama</strong>: A platform enabling local execution of LLMs.</li>
</ul>

<h2>Features</h2>
<ul>
    <li>Contextual Responses: Provides relevant and accurate answers.</li>
    <li>Local LLM Integration: Enhances privacy and reduces latency.</li>
    <li>Modular Architecture: Easily extendable and modifiable.</li>
</ul>

<h2>Getting Started</h2>
<h3>Prerequisites</h3>
<ul>
    <li>JDK 17 or higher</li>
    <li>Maven</li>
    <li>Ollama installed and configured</li>
    <li>Docker installed and runned</li>
</ul>

<h3>Installation</h3>
<ol>
    <li>Clone the repository:
        <pre><code>git clone https://github.com/MAROUANELK02/RAG_With_DeepSeek.git</code></pre>
    </li>
    <li>Install dependencies:
        <pre><code>mvn clean install</code></pre>
    </li>
    <li>Run the application:
        <pre><code>mvn spring-boot:run</code></pre>
    </li>
</ol>

<h2>Usage</h2>
<p>Once running, interact with the chatbot via API endpoints. It retrieves relevant information and generates contextually appropriate responses.</p>

<h2>Contributing</h2>
<p>Contributions are welcome! Fork the repository and submit a pull request with your enhancements or bug fixes.</p>

<h2>License</h2>
<p>Licensed under the MIT License. See the <code>LICENSE</code> file for details.</p>

<h2>Acknowledgements</h2>
<ul>
    <li><a href="https://github.com/langchain4j/langchain4j">Langchain4j</a></li>
    <li><a href="https://ollama.com/">Ollama</a></li>
</ul>
