# quarkus-service1
To run the application with docker you need to clone 2 repositories:
- https://github.com/DavidSchiavone/quarkus-service1
- https://github.com/DavidSchiavone/quarkus-service2

Docker has to be running.  
First you have to start quarkus-service1 in DEV-Mode and then you have to start quarkus-service2.

## Information
The application offers a blog backend that enriches blog entries with data using AI.

## API
The API calls are listed below.
If you have Postman you can import the following Postman-Collection [LangChain4J.postman_collection.json](resources/LangChain4J.postman_collection.json)

### Get Blogs
Returns the list of Blogs.
```http request
GET localhost:8080/blogs/
```

Response:
```json
[
    {
        "blogid": 2,
        "content": "Die Hochschule für Technik und Management (HFTM) in Grenchen ist ein Leuchtturm der Informatikbildung in der Region. Sie vereint praxisnahe Lehre mit modernster Technologie und bereitet die Studierenden optimal auf die Anforderungen des digitalen Zeitalters vor. Im Fokus stehen nicht nur theoretische Grundlagen, sondern auch die Entwicklung realer Softwareprojekte. Der interdisziplinäre Ansatz fördert kreatives Denken und innovative Lösungen. Studierende profitieren von exzellenten Laboreinrichtungen und einer engen Vernetzung mit der Industrie. Durch Projektarbeiten und Praktika vertiefen sie ihr Wissen in praktischen Anwendungen. Die HFTM Grenchen bietet somit einen optimalen Start in eine erfolgreiche Karriere im Bereich Informatik. Die Absolventen sind gefragte Fachkräfte und tragen aktiv zur digitalen Transformation in Unternehmen bei. Damit wird die HFTM zu einem wichtigen Akteur in der Schweizer Bildungslandschaft.",
        "metaDescription": "Entdecken Sie die Hochschule für Technik und Management in Grenchen – ein Zentrum für praxisnahe Informatikbildung, moderne Technologien und innovative Lösungen für Ihre Zukunft.",
        "metaTitle": "HFTM Grenchen: Ihr Weg zur erfolgreichen Karriere in der Informatik",
        "summary": "Die HFTM Grenchen bietet praxisnahe Informatikbildung, fördert kreatives Denken und verbindet Studierende optimal mit der Industrie, um sie auf eine erfolgreiche Karriere vorzubereiten.",
        "tags": "HFTM Grenchen, Informatikbildung, Karriere in der Informatik, Praxisnahe Lehre, Digitale Transformation",
        "title": "HFTM Grenchen: Ein Zentrum für Informatik und Innovation",
        "valid": true
    },
    {
        "blogid": 3,
        "content": "Die HFTM ist dumm",
        "title": "HFTM",
        "valid": false
    }
]
```

### Post blog
Adds a new blog-entry.
```http request
POST localhost:8080/blogs/
```

Request-Body:
```json
{
    "content": "Sie sind ein kluger Kopf, ein kreativer Denker und Lösungsfinder? Sie verstehen die digitale Welt nicht nur, Sie können und wollen sie auch gestalten, entwickeln, in das Arbeitsleben implementieren. Mit anderen Worten, Sie haben das Zeug, Computerprogramme für verschiedenste Plattformen zu erstellen, zu betreiben und zu unterhalten. Mit dem eidgenössisch anerkannten Studium zum/zur Dipl. Informatiker/-in HF, Schwerpunkt Softwareentwicklung bewegen Sie sich elegant und sicher auf der Datenautobahn und sind unterwegs, ein kompletter «Software Developer/-in» zu werden.",
    "title": "Erstellen, betreiben und unterhalten von Computeranwendungen"
}
```

### Generate Blog
Generates a new blog-entry with AI
```http request
POST localhost:8080/blogs/generate
```

Request-Body:
```json
{
    "topic": "Australien",
    "keywords": [
        "Tierwelt"
    ]
}
```

### Q&A
Answers questions to a blog-entry with AI
```
GET localhost:8080/blogs/{blogId}/qa
```

Request-Body:
```json
{
    "question": "Um welches Land handelt es sich bei dem Eintrag?"
}
```

Response:
```json
{
    "response": "Der Eintrag handelt von Australien."
}
```

### Find similar Blog-entries
Returns a list of similar blog-entries
```
localhost:8080/blogs/{blogId}/similars
```

Response:
```json
[
    {
        "blogid": 2,
        "content": "Die Hochschule für Technik und Management (HFTM) in Grenchen ist ein Leuchtturm der Informatikbildung in der Region. Sie vereint praxisnahe Lehre mit modernster Technologie und bereitet die Studierenden optimal auf die Anforderungen des digitalen Zeitalters vor. Im Fokus stehen nicht nur theoretische Grundlagen, sondern auch die Entwicklung realer Softwareprojekte. Der interdisziplinäre Ansatz fördert kreatives Denken und innovative Lösungen. Studierende profitieren von exzellenten Laboreinrichtungen und einer engen Vernetzung mit der Industrie. Durch Projektarbeiten und Praktika vertiefen sie ihr Wissen in praktischen Anwendungen. Die HFTM Grenchen bietet somit einen optimalen Start in eine erfolgreiche Karriere im Bereich Informatik. Die Absolventen sind gefragte Fachkräfte und tragen aktiv zur digitalen Transformation in Unternehmen bei. Damit wird die HFTM zu einem wichtigen Akteur in der Schweizer Bildungslandschaft.",
        "metaDescription": "Entdecken Sie die Hochschule für Technik und Management in Grenchen – ein Zentrum für praxisnahe Informatikbildung, moderne Technologien und innovative Lösungen für Ihre Zukunft.",
        "metaTitle": "HFTM Grenchen: Ihr Weg zur erfolgreichen Karriere in der Informatik",
        "summary": "Die HFTM Grenchen bietet praxisnahe Informatikbildung, fördert kreatives Denken und verbindet Studierende optimal mit der Industrie, um sie auf eine erfolgreiche Karriere vorzubereiten.",
        "tags": "HFTM Grenchen, Informatikbildung, Karriere in der Informatik, Praxisnahe Lehre, Digitale Transformation",
        "title": "HFTM Grenchen: Ein Zentrum für Informatik und Innovation",
        "valid": true
    }
]
```