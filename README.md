# Formatix

Formatix is a web application that allows generating documents from JSX templates (React) in formats such as PDF, Microsoft Word (DOCX, Office Open XML –
WordProcessingML), LibreOffice/OpenOffice Writer (ODT, OpenDocument Text), HTML, or plain text.

For generating PDF documents, the [Apache FOP](https://xmlgraphics.apache.org/fop/) library is used.

## Installation

The application requires Java (JRE or JDK) version 21 or higher to run. GraalVM (Community Edition or Oracle GraalVM) is recommended, as it offers better
support for running JavaScript code.

To start, simply [download](https://github.com/jinotaj/Formatix/releases) the distribution ZIP file (`formatix-<version>.zip`), extract it, configure it, and
run `./bin/formatix` (MacOS or Linux) or `./bin/formatix.bat` (Windows).

## Configuration

The application is configured using the `application.yml` file, which is located in the `config` directory within the application's root folder, or directly in
the root folder of the application. This is a [Micronaut framework configuration file](https://docs.micronaut.io/4.6.3/guide/#config).

You need to configure the path to the Apache FOP configuration directory. This directory must contain the [configuration file
`fop.xconf`](https://xmlgraphics.apache.org/fop/2.9/configuration.html).

You also need to configure the path to the JSX templates.

### Configuration Example

```yaml
fop:
  dir: /path/to/fop-config
content:
  root: /path/to/content
```

## Usage

The directory with JSX templates (content) must contain an `index.js` file, which exports a default function that receives a React (JSX) component and a
JavaScript object (`props`) as parameters, and returns the rendered output (XML, such as XSL-FO) as a `String`. The `props` object is created from the JSON data
passed in the body of the HTTP request when calling the template.

### Example `index.jsx`

```jsx
import {renderToStaticMarkup} from 'react-dom/server.browser';

export default (Page, props) => {
    const result = renderToStaticMarkup(<Page {...props} />);
    return result;
}
```

Additionally, the directory with JSX templates must contain a `pages` folder. Individual HTTP requests are then mapped to files in this folder. For example, a
request to `/example` maps to the file `pages/example.jsx`, and a request to `/envelope/C6` maps to the file `pages/envelope/C6.jsx`.

All TypeScript, JSX, or TSX files must be compiled into JavaScript before running the application.

An almost empty template for content is available in the [formatix-content-template](https://github.com/jinotaj/formatix-content-template) repository. After
running `npm run build`, a `dist` directory will be created, containing the compiled content files – the path to this directory should be specified in the
Formatix configuration.
