# Pattern Matcher CLI

This CLI program performs pattern matching operations on data provided in files.

## Usage

### Run the program

To start the pattern-matching process, use the `run` command with the required options.

```sh
java -jar target/pattern-matcher-1.0.jar run [options]
```

### Options for the `run` command:

- `-s, -sequence`  
  **Required**. The absolute path to the sequencing data file.

- `-c, -config`  
  **Required**. The absolute path to the configuration file.

- `-o, -outputPathPrefix`  
  **Required**. The path prefix to the output files.

- `-a, -alignment`  
  **Required**. The name of the alignment type. Must be one of:
  - `endsAlignment`
  - `midAlignment`
  - `bestAlignment`

### Example Usage:

To print the usage of the program:

```bash
java -jar target/pattern-matcher-1.0.jar help
```
To run the pattern matching process with required arguments:

```bash
java -jar target/pattern-matcher-1.0.jar run -s /path/to/sequencing_file.seq -c /path/to/config_file.conf -o /path/to/output/ -a endsAlignment
```