<h1>SimpleJsonORM</h1>

<p>SimpleJsonORM is a lightweight Java library that simplifies working with JSON data and provides a minimalistic Object-Relational Mapping (ORM) solution. The library is designed to be easy to use and integrate into your Java projects.</p>

<h2>Features</h2>

<ul>
  <li>Abstract classes and interfaces for working with entities and repositories</li>
  <li>JSON database support for simple storage and retrieval of objects</li>
  <li>Extendable to accommodate various data storage mechanisms</li>
</ul>

<h2>Project Structure</h2>

<pre>
com.semdevko.simplejsonorm
│
├── BaseEntity
├── AbstractJsonRepository
├── JsonDB
│
└── core
    ├── Entity
    ├── Repository
    └── JsonDatabase
</pre>

<h2>Getting Started</h2>

<h3>Prerequisites</h3>

<ul>
  <li>Java 8 or later</li>
</ul>

<h3>Installation</h3>

<ol>
  <li>Clone this repository or download the source code.</li>
  <li>Build the project using your preferred Java build tool (e.g., Maven, Gradle).</li>
  <li>Include the built JAR file in your project's classpath.</li>
</ol>

<h3>Usage</h3>

<ol>
    <li>
<p>Create a class that represents the entity you want to persist. This class should extend the <code>BaseEntity</code> class.</p>
<pre>
public class MyEntity extends BaseEntity {
    // Your entity fields and methods here
}
</pre>
<p>Alternatively, you can choose to implement the <code>Entity</code> interface instead.</p>
    </li><hr>
    <li>
<p>Create a repository class for your entity that extends <code>AbstractJsonRepository</code> passing your custom object as a generic type.</p>
<pre>
public class MyEntityRepository extends AbstractJsonRepository&lt;MyEntity&gt; {
    // Your repository implementation here
}
</pre>
<p>
It is not possible to use the <code>AbstractRepository</code> class without supplying a type that implements the <code>Entity</code> interface. 
The current implementation requires the supplied type to have an <code>id</code> field of type <code>int</code>.
</p><br>
<p>If the id is not set, the <code>AbstractRepository</code> will assign it a value that is higher than the highest saved <code>id</code> for that type.</p>
<br>
<p>
You can also choose to implement the <code>Repository</code> interface manually. 
However, this is not recommended as the <code>AbstractRepository</code> class handles a significant amount of functionality.
</p>
    </li><hr>
    <li>
<p>Instantiate the <code>JsonDB</code> class and use the <code>MyEntityRepository</code> class to persist and retrieve entities. Note that this instance of JsonDB can be used for multiple repositories all handling different Entities.</p>
<pre>
public class Main {
    public static void main(String[] args) {
        JsonDB jsonDB = new JsonDB("path/to/json/file.json");
        MyEntityRepository myEntityRepository = new MyEntityRepository(jsonDB);
        &nbsp;
        // Create, update, delete, and retrieve entities using myEntityRepository
    }
}   
</pre>
    </li>
</ol>