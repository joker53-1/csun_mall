package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "csr_member_member_tag")
public class CsrMemberMemberTag {
    @Id
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "tag_id")
    private Long tagId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return member_id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return tag_id
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * @param tagId
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}