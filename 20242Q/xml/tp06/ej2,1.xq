<projects>
{
    for $project in doc("projects.xml")//projects/project

    return 
    
        <project name="{$project/@name}">

        {doc("clients.xml")//clients/client[@name=$project/client/@name]}

        {$project/dates}

        {$project/platform}

        <staff>
            {
            for $person in doc("persons.xml")//persons/person
            where $person/@document = $project/staff/person/@document
            return $person
            }
        </staff>

        </project>
}
</projects>