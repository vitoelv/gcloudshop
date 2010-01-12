package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class Article extends ModelObject {
   
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String articleId; 

  @Persistent
  private java.lang.String catId; 

  @Persistent
  private java.lang.String title; 

  @Persistent
  private java.lang.String content; 

  @Persistent
  private java.lang.String author; 

  @Persistent
  private java.lang.String authorEmail; 

  @Persistent
  private java.lang.String keywords; 

  @Persistent
  private java.lang.Long articleType=0l; 

  @Persistent
  private java.lang.Boolean isOpen=false; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.String fileUrl; 

  @Persistent
  private java.lang.Long openType=0l; 

  @Persistent
  private java.lang.String link; 



	public Article() {
	}



  public java.lang.String getArticleId() {
    return articleId;
  }

  public void setArticleId(java.lang.String newArticleId) {
    articleId = newArticleId;
  }



  public java.lang.String getCatId() {
    return catId;
  }

  public void setCatId(java.lang.String newCatId) {
    catId = newCatId;
  }



  public java.lang.String getTitle() {
    return title;
  }

  public void setTitle(java.lang.String newTitle) {
    title = newTitle;
  }



  public java.lang.String getContent() {
    return content;
  }

  public void setContent(java.lang.String newContent) {
    content = newContent;
  }



  public java.lang.String getAuthor() {
    return author;
  }

  public void setAuthor(java.lang.String newAuthor) {
    author = newAuthor;
  }



  public java.lang.String getAuthorEmail() {
    return authorEmail;
  }

  public void setAuthorEmail(java.lang.String newAuthorEmail) {
    authorEmail = newAuthorEmail;
  }



  public java.lang.String getKeywords() {
    return keywords;
  }

  public void setKeywords(java.lang.String newKeywords) {
    keywords = newKeywords;
  }



  public java.lang.Long getArticleType() {
    return articleType;
  }

  public void setArticleType(java.lang.Long newArticleType) {
    articleType = newArticleType;
  }



  public java.lang.Boolean getIsOpen() {
    return isOpen;
  }

  public void setIsOpen(java.lang.Boolean newIsOpen) {
    isOpen = newIsOpen;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(java.lang.String newFileUrl) {
    fileUrl = newFileUrl;
  }



  public java.lang.Long getOpenType() {
    return openType;
  }

  public void setOpenType(java.lang.Long newOpenType) {
    openType = newOpenType;
  }



  public java.lang.String getLink() {
    return link;
  }

  public void setLink(java.lang.String newLink) {
    link = newLink;
  }

}
