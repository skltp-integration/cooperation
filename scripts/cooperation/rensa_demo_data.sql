DELETE FROM COOPERATION WHERE SERVICE_CONTRACT_ID IN (SELECT id FROM SERVICECONTRACT where id > 25)
DELETE FROM SERVICEPRODUCTION WHERE SERVICE_CONTRACT_ID IN (SELECT id FROM SERVICECONTRACT where id > 25)
DELETE FROM SERVICECONSUMER WHERE ID NOT IN (SELECT SERVICE_CONSUMER_ID FROM COOPERATION)
DELETE FROM SERVICEPRODUCER WHERE ID NOT IN (SELECT SERVICE_PRODUCER_ID FROM SERVICEPRODUCTION)