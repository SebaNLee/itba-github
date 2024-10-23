<persons>
{
    for $person in doc("persons.xml")//person
    return $person[age > 27]
}
</persons>