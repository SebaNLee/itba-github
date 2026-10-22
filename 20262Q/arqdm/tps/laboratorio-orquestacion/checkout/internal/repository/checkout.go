package repository

import "gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/pkg/model/checkout"

type CheckoutRepository interface {
	Save(checkout *checkout.Checkout) error
}
