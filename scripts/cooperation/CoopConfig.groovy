//environments = ["ntjp_prod", "sll_prod", "ntjp_qa", "sll_qa", "ntjp_test", "ntjp_dev"]
environments = System.getenv('COOPERATION_IMPORT_ENVIRONMENTS').tokenize(',')
