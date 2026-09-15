import crypto from 'crypto';
import gql from 'graphql-tag';
import { PubSub } from 'graphql-subscriptions';

//Import the data from _db.js
import { restaurants, menuItems, orders } from './_db.js';

const pubsub = new PubSub();
//Make a GraphQL Schema about a restaurant and its menu
export const typeDefs = gql`
    type Restaurant {
        id: ID!
        name: String!
        location: String
        menu: [MenuItem!]!
    }
    type MenuItem {
        id: ID!
        name: String!
        price: Float!
        description: String
    }
    type Order {
        id: ID!
        restaurant: Restaurant!
        items: [MenuItem!]!
        total: Float!
        status: String!
    }
    ## Query type to fetch data
    type Query {
        restaurants: [Restaurant!]!
        restaurant(id: ID!): Restaurant
        orders: [Order!]!
        order(id: ID!): Order
    }
    ## Mutation type to modify data
    type Mutation {
        newOrder(restaurantId: ID!, items: [ID!]!): Order!
    }
    ## Subscription type for real-time updates
    type Subscription {
        orderCreated: Order!
        orderUpdated: Order!
    }
    
`;

export const resolvers = {
    Query: {
        restaurants: () => restaurants,
        restaurant: (_, args) => restaurants.find(restaurant => restaurant.id === args.id),
        orders: () => orders,
        order: (_, args) => orders.find(order => order.id === args.id),
    },
    Restaurant: {
        menu: (restaurant) => restaurant.menu.map(itemId => menuItems.find(menuItem => menuItem.id === itemId)),
    },
    Order:{
        restaurant: (order) => restaurants.find(restaurant => {
            return restaurant.id === order.restaurant
        }),
        items: (order) => order.items.map(item => menuItems.find(menuItem =>{
            return menuItem.id === item
        })),
    },
    Mutation: {
        newOrder: (_, { restaurantId, items }) => {
            const restaurant = restaurants.find(r => r.id === restaurantId);
            if (!restaurant) throw new Error('Restaurant not found');

            const orderItems = items.map(itemId => {
                const item = menuItems.find(menuItem => menuItem.id === itemId);
                if (!item) throw new Error(`Menu item with ID ${itemId} not found`);
                return item;
            });

            const total = orderItems.reduce((sum, item) => sum + item.price, 0);

            const newOrder = {
                id: crypto.randomUUID(),
                restaurant: restaurantId,
                items: items,
                total,
                status: 'PENDING',
            };

            orders.push(newOrder);

            // Helper para publicar el order resuelto (sin IDs de referencia)
            const resolveOrder = (order) => ({
                ...order,
                restaurant: restaurants.find(r => r.id === order.restaurant),
                items: order.items.map(itemId => menuItems.find(m => m.id === itemId)),
            });

            pubsub.publish('ORDER_CREATED', { orderCreated: resolveOrder(newOrder) });

            // Simulación del cocinado de la orden
            setTimeout(() => {
                newOrder.status = 'COOKING';
                pubsub.publish('ORDER_UPDATED', { orderUpdated: resolveOrder(newOrder) });
                setTimeout(() => {
                    newOrder.status = 'READY';
                    pubsub.publish('ORDER_UPDATED', { orderUpdated: resolveOrder(newOrder) });
                }, 3000); // 3 segundos para pasar a READY
            }, 2000); // 2 segundos para pasar a COOKING

            return newOrder;
        },
    },
    Subscription: {
        orderCreated: {
            subscribe: () => pubsub.asyncIterableIterator(['ORDER_CREATED']),
        },
        orderUpdated: {
            subscribe: () => pubsub.asyncIterableIterator(['ORDER_UPDATED']),
        },
    },

}