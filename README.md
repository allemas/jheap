
# **JHeap - Lightweight JVM Heap Monitoring APM**

**JHeap** is an open-source Application Performance Monitoring (APM) tool designed to dynamically attach to a running JVM and provide real-time insights into heap memory usage. The tool specializes in heap management and garbage collection (GC) observability with minimal external dependencies. JHeap is lightweight, self-contained, and perfect for production environments where restarting the application is not an option.

---

## **Key Features**

- **Dynamic JVM Attachment**:
  - Seamlessly attach to a running JVM without requiring application restarts using the `com.sun.tools.attach` API.

- **Heap Metrics Collection**:
  - Retrieve detailed heap memory usage metrics, including:
    - Total heap memory
    - Used heap memory
    - Free heap memory
    - Garbage collector (GC) statistics
  
- **Garbage Collector (GC) Monitoring**:
  - Track minor and major GC cycles.
  - Measure the time spent in GC and its impact on overall performance.
  - Log GC events for later analysis.

- **Local Metrics Storage**:
  - Persist metrics locally on the machine for later analysis using efficient file formats like Parquet/Avro.
  
- **Customizable Sampling**:
  - Define the sampling intervals and data points to collect, tailored to specific performance needs.

- **Minimal External Dependencies**:
  - JHeap is lightweight, requiring no external monitoring tools or infrastructure, making it ideal for production environments with minimal overhead.

---

## **Getting Started**

### **Prerequisites**

- **Java Development Kit (JDK)**: Version 8 or higher
- **Maven** (optional, for building the project)

### **Installation**

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

---

## **Usage**

### **Identify Target JVM**:
Use the following command to list all running JVMs:
```bash
jps -l
```
Identify the process ID (PID) of the target JVM.

### **Attach to JVM**:
Run JHeap with the target PID:
```bash
java -jar jheap-1.0.0.jar --pid=<TARGET_PID>
```

### **Access Metrics**:
Metrics will be written to a file locally in Parquet/Avro format by default. You can customize the file path using command-line arguments.

---

## **Command-Line Options**

| Option           | Description                             | Default Value  |
|------------------|-----------------------------------------|----------------|
| `--pid`          | PID of the target JVM                  | Required       |
| `--output`       | Path to the output metrics file         | ./metrics.parquet |
| `--interval`     | Sampling interval in milliseconds      | 5000           |

---

## **Metrics Output**

The metrics will be stored in Parquet/Avro format, optimized for performance and structured queries. Use tools like Apache Arrow or Spark to read and analyze the files.

---

## **Roadmap**

- Simplify the user experience with minimal setup.
- Focus on providing a standalone JAR with robust, self-contained functionality.
- Enhance heap analysis capabilities.
- Introduce visualization tools in future versions.
- Add support for exporting metrics in JSON format.
- Implement Prometheus-compatible endpoint for metrics export.

---

## **Contributing**

We welcome contributions! To get started:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Submit a pull request with a clear description of your changes.

---
