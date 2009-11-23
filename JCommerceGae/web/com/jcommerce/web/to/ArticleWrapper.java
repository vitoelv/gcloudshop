package com.jcommerce.web.to;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;

public class ArticleWrapper extends BaseModelWrapper implements URLConstants{
	Article article;
	@Override
	protected Object getWrapped() {
		return getArticle();
	}
	public ArticleWrapper(ModelObject article) {
		super();
		this.article = (Article)article;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
		
	
}
