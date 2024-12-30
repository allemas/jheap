# JHeap - A Lightweight JVM Heap Monitoring APM

**JHeap** is a lightweight, open-source Application Performance Monitoring (APM) tool designed to dynamically connect to a running JVM and provide real-time insights into the heap memory usage. It allows developers and operators to monitor and diagnose memory-related issues without restarting the application.

---

## Key Features

- **Dynamic JVM Attachment**: Seamlessly attach to a running JVM without requiring application restarts, leveraging the `com.sun.tools.attach` API.
- **Heap Metrics Extraction**: Retrieve detailed information about heap usage, including:
    - Total heap memory
    - Used heap memory
    - Free heap memory
    - Garbage collector statistics
- **Local Metrics Storage**: Persist the metrics locally on the machine for later analysis using efficient file formats like Parquet.
- **Customizable Sampling**: Define intervals and data points for metric collection to fit your performance needs.
- **Simple Integration**: Minimal setup required for monitoring Java processes.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **Maven** (optional, for building the project)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/jheap.git
   cd jheap
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the tool:
   ```bash
   java -jar target/jheap-1.0.0.jar
   ```

### Usage

1. **Identify Target JVM**:
   Use the following command to list all running JVMs:
   ```bash
   jps -l
   ```
   Identify the process ID (PID) of the target JVM.

2. **Attach to JVM**:
   Run JHeap with the target PID:
   ```bash
   java -jar jheap-1.0.0.jar --pid=<TARGET_PID>
   ```

3. **Access Metrics**:
   Metrics will be written to a file locally in Parquet format by default. You can customize the file path using command-line arguments.

### Command-Line Options

| Option           | Description                             | Default Value  |
|------------------|-----------------------------------------|----------------|
| `--pid`          | PID of the target JVM                  | Required       |
| `--output`       | Path to the output metrics file         | ./metrics.parquet |
| `--interval`     | Sampling interval in milliseconds      | 5000           |

---

## Example Metrics Output

The metrics will be stored in Parquet format, optimized for performance and structured queries. Use tools like Apache Arrow or Spark to read and analyze the files.

---

## Roadmap

- Simplify user experience with minimal setup.
- Focus on providing a standalone JAR with robust, self-contained functionality.
- Enhance heap analysis capabilities.
- Introduce visualization tools in future versions.
- Add support for exporting metrics in JSON format.
- Implement Prometheus-compatible endpoint for metrics export.

---

## Contributing

We welcome contributions! To get started:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Submit a pull request with a clear description of your changes.

---