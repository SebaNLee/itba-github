let $persons := for $person in doc("projects.xml")//projects/project[platform/@name = "Java"]/staff/person
return data(doc("persons.xml")//persons/person[@document = $person/@document])
return distinct-values($persons)

(: ?? lo copie de soluciones ?? :)