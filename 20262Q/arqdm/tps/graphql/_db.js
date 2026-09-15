//Mock data para el esquema de restaurante

export const restaurants = [
    {
        id: "1",
        name: "La Parrilla Argentina",
        location: "Madrid",
        menu: [
            "1",
            "2"
        ]
    },
    {
        id: "2",
        name: "Sushi Sakura",
        location: "Barcelona",
        menu: [
            "3",
            "4"
        ]
    }
];

export const menuItems = [
    { id: "1", name: "Bife de chorizo", price: 18.5, description: "Corte argentino de carne a la parrilla" },
    { id: "2", name: "Empanadas", price: 3.5, description: "Empanadas de carne tradicionales" },
    { id: "3", name: "Sushi Moriawase", price: 22.0, description: "Surtido de sushi variado" },
    { id: "4", name: "Ramen", price: 12.0, description: "Ramen de cerdo con huevo" }
];

export const orders = [
    {
        id: "1",
        restaurant: "1",
        items: ["1", "2"],
        total: 22.0,
        status: "TERMINADO"
    },
];